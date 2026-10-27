# Spritan: Project Overview

Written for developers picking up the codebase. For setup and usage, see [README.md](README.md).

## What it is

Spritan is a single-window desktop pixel-art editor. It is a small codebase (about 1,700 lines across 16 files) with no tests and no dependencies beyond JavaFX.

| | |
| --- | --- |
| Language | Java (compiled with `--release 11`) |
| UI toolkit | JavaFX 19 (`javafx-controls`) |
| Build | Maven, `javafx-maven-plugin` 0.0.8 |
| Modules | JPMS: `com.elrogbany.sptitan`, requires `javafx.controls` and `java.desktop` |
| Main class | `com.elrogbany.sptitan.App` |

`java.desktop` is required for `BufferedImage` / `ImageIO`, which the image export uses.

## Architecture

The code is split into four packages under `com.elrogbany.spritan`, plus the `App` entry point.

```
                      ┌────────────────────────────┐
                      │            App             │  window, history stacks,
                      │  (singleton: App.instance) │  grid replacement, zoom
                      └──────┬───────────┬─────────┘
                             │           │
              builds UI      │           │ owns
        ┌────────────────────┘           └───────────────┐
        ▼                                                ▼
┌──────────────────────────────┐                ┌─────────────────┐
│ ui                           │   draws/reads  │ SpritanGrid     │
│ SpritanMenu, SpritanToolbar, │◄──────────────►│ (canvas view +  │
│ SpritanLayerToolbar,         │                │  input handling)│
│ SpritanColorPaletteBar       │                └───┬─────────┬───┘
└──────────────┬───────────────┘                    │         │ delegates clicks
               │ calls                              │ holds   ▼
               ▼                                    │   ┌───────────────┐
┌──────────────────────────────┐                    │   │ tools         │
│ utilities                    │                    │   │ ITool         │
│ SpritanFileOperations        │                    │   │ PenTool       │
│ SpritanColorPaletteFile...   │                    │   │ EraserTool    │
│ SpritanColor                 │                    │   └───────┬───────┘
└──────────────┬───────────────┘                    ▼           │ mutate
               │ (de)serializes              ┌──────────────────▼──────┐
               └────────────────────────────►│ core                    │
                                             │ SpritanFile             │
                                             │  ├─ SpritanLayer[]      │
                                             │  ├─ SpritanColorPalette │
                                             │  └─ selected color/layer│
                                             └─────────────────────────┘
```

### `App`

[App.java](src/main/java/com/elrogbany/sptitan/App.java) builds the scene (menu bar on top, tool bar / canvas / palette bar in the middle, layer bar at the bottom) and holds the application-wide state:

- `App.instance` is a static singleton that other classes use to reach the current grid and the undo/redo functions.
- `CreateGrid(...)` replaces the canvas, wrapping the new `GridPane` in a `ScrollPane` and installing the mouse-wheel zoom handler.
- `SaveState()`, `Undo()` and `Redo()` manage two stacks of `Memento` snapshots.

Initially there is no canvas; the user creates or opens a file first.

### `core`: the data model

No JavaFX types are used here, so this layer is independent of the UI.

- **`SpritanFile`** is the document. It holds the list of layers, the selected layer index, the selected color, the color palette, and the canvas size. `GetBlendedImage()` composites all layers into a single 2D grid of colors, iterating from the top layer down and using `SpritanColor.BlendTwoColors`.
- **`SpritanLayer`** is a 2D `ArrayList<ArrayList<SpritanColor>>` of pixels, initialised to `TRANSPARENT`. It has an optional name; if unset, the UI shows "Layer N".
- **`SpritanColorPalette`** is a list of `SpritanColor`.

All three implement `Serializable` and `Cloneable` with deep-copy `clone()` methods. Cloning is what makes undo/redo work; serialization is what makes save/load work.

### `tools`

`ITool` has a single method, `OnPixelClick(grid, pixel, row, column)`. `PenTool` and `EraserTool` each call `App.instance.SaveState()` first, then modify the selected layer through `SpritanFile`, then ask the grid to redraw. Adding a tool means implementing `ITool` and registering a button and shortcut in `SpritanToolbar`.

### `ui`

- **`SpritanGrid`** is the canvas. It creates a `GridPane` of 20x20 `Rectangle` nodes, one per pixel, and translates mouse click/drag coordinates into a (row, column). It remembers the last pixel handled so a drag over the same pixel doesn't fire the tool repeatedly. `DrawSavedImage()` repaints every rectangle from the blended image. It also defines the `Memento` class used for undo/redo.
- **`SpritanMenu`** defines the File and Edit menus and their keyboard accelerators, and contains the export file-chooser logic.
- **`SpritanToolbar`** is the vertical tool bar. Tool shortcuts (`P`, `E`) are scene-level key event filters.
- **`SpritanLayerToolbar`** lists layers as buttons and handles add/remove/select.
- **`SpritanColorPaletteBar`** shows the palette as a two-column swatch grid, a color-picker dialog, the selected-color indicator, and New / Save / Load palette buttons.

These UI classes expose static factory methods (`GetMenuBar`, `GetToolbar`, ...) and keep their widgets in static fields, so each can only exist once per JVM.

### `utilities`

- **`SpritanColor`** is a serializable RGBA color with hex conversion, lighten/darken, a blend helper, and the `BlendTwoColors` alpha-compositing function used for layer flattening.
- **`SpritanFileOperations`** implements New (size dialog), Open, Save, Save As and image export. Project files are written with `ObjectOutputStream`; export converts the blended image to a `BufferedImage` and writes it with `ImageIO`.
- **`SpritanColorPaletteFileOperations`** saves and loads `.scp` palette files the same way.

## Key flows

**Drawing a pixel.** Mouse event on the `GridPane` -> `SpritanGrid.HandleDrag` computes row/column -> `currentTool.OnPixelClick` -> `App.SaveState()` pushes a cloned `SpritanFile` and clears the redo stack -> the tool updates the selected layer -> `DrawSavedImage()` re-blends all layers and repaints.

**Undo / redo.** Undo pops the last snapshot, pushes a snapshot of the current state onto the redo stack, and swaps the grid's `SpritanFile` for the restored one, then redraws the canvas, layer bar and palette bar. Redo is the mirror image. Every undo step is a full deep copy of the document.

**Save / open.** Save serializes the whole `SpritanFile` to a `.spt` file. Open deserializes it, derives the canvas size from the first layer's dimensions, and builds a new `SpritanGrid` around it.

## Known limitations

These are observations from reading the code, not a roadmap.

- **No tests.** The model layer is UI-free and would be straightforward to unit test (blending, layer add/remove, clone independence).
- **Memory use of undo.** History is unbounded, and every step deep-copies all layers pixel by pixel.
- **Rendering cost.** `GetNodeAt` scans every child of the grid, and `DrawSavedImage` calls it for every pixel, so a full redraw is O(n^2) in the pixel count. Large canvases will be slow.
- **Input validation.** The New File dialog calls `Integer.parseInt` directly; non-numeric input throws instead of showing an error. Open and Save errors go to `printStackTrace` rather than the UI.
- **JPG export.** Export builds an ARGB image; the JDK's JPEG writer generally does not accept images with an alpha channel, so JPG export likely produces no file. PNG is the reliable format. This was not run to confirm.
- **Serialization format.** Project files use Java object serialization, so they are tied to the class layout and are not safe to open from untrusted sources.
- **Layers.** No reordering, renaming, visibility toggle or opacity control, although `SpritanLayer` already has a `name` field. Removing a layer resets the selection to the first layer.
- **Singleton / static UI state.** `App.instance` and the static widget fields couple the UI classes together and make them hard to reuse or test.
- **Naming.** The `sptitan` / `spritan` spelling split (see the README) is baked into the Maven artifact, the module name and the package layout.
- **Repository hygiene.** `target/` build output is tracked in git despite no matching `.gitignore` rule.

## Development history

The git log reads as an incremental build-out: initial grid and toolbar, tools, color palette, palette save/load, a custom `.spt` file extension, layers, undo/redo with a memento pattern, keyboard shortcuts, scroll pane and zoom, and finally image export.
