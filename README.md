# Spritan

A desktop pixel-art editor built with Java and JavaFX. Draw sprites on a grid, work in layers, build reusable color palettes, and export your work as an image.

> The Maven artifact and the entry-point package are spelled `Sptitan` / `sptitan`; the application and all other packages are spelled `Spritan`. This is the existing naming in the code, not a typo in this document.

## Features

- **Canvas of any size**: choose the height and width (in pixels) when creating a new file.
- **Drawing tools**: Pen and Eraser, with click-and-drag painting.
- **Layers**: add, remove and select layers. Layers are alpha-blended top-to-bottom for display and export.
- **Color palettes**: build a palette with a color picker, then save and load palettes as `.scp` files.
- **Undo / Redo**: every edit (pixel, layer, palette change) can be undone and redone.
- **Zoom and scroll**: mouse-wheel zoom (0.5x to 3x) over the canvas, with scroll bars for large images.
- **Save / Open**: projects are stored as `.spt` files, including layers, palette and selected color.
- **Image export**: export the blended image as PNG, JPG or BMP.

## Requirements

- JDK 11 or newer
- Maven 3.x

JavaFX 19 (`javafx-controls`) is pulled in by Maven; you don't need to install it separately.

## Running

```bash
mvn clean javafx:run
```

To attach a debugger on port 8000 (suspends until a debugger connects):

```bash
mvn clean javafx:run@debug
```

## Usage

1. **File > New** (`Ctrl/Cmd+N`) and enter a height and width to create a canvas.
2. Pick a tool (**Pen** or **Eraser**). No tool is active until you choose one.
3. Add colors to the palette on the right with the grey `+` swatch, then click a swatch to select it. The selected color is shown beneath the palette.
4. Click or drag on the canvas to draw. Scroll the mouse wheel over the canvas to zoom.
5. Use the layer bar at the bottom to add, remove or switch layers.
6. **File > Save** to keep your work, or **File > Export** to write a PNG/JPG/BMP.

### Keyboard shortcuts

| Action | Shortcut |
| --- | --- |
| New file | `Ctrl/Cmd+N` |
| Open | `Ctrl/Cmd+O` |
| Save | `Ctrl/Cmd+S` |
| Save As | `Ctrl/Cmd+Shift+S` |
| Export image | `Ctrl/Cmd+Shift+Q` |
| Undo | `Ctrl/Cmd+Z` |
| Redo | `Ctrl/Cmd+Y` |
| Pen tool | `P` |
| Eraser tool | `E` |

### File formats

| Extension | Contents |
| --- | --- |
| `.spt` | A whole project (layers, palette, selected color), saved with Java object serialization |
| `.scp` | A single color palette, saved with Java object serialization |
| `.png` / `.jpg` / `.bmp` | Flattened image export |

## Project structure

```
src/main/java/
├── module-info.java
└── com/elrogbany/
    ├── sptitan/App.java            # Application entry point, window layout, undo/redo history
    └── spritan/
        ├── core/                   # Data model (no UI)
        ├── tools/                  # Drawing tools
        ├── ui/                     # JavaFX views
        └── utilities/              # Color math and file I/O
src/main/resources/css/style.css    # Application styling
```

See [PROJECT_OVERVIEW.md](PROJECT_OVERVIEW.md) for an architecture walkthrough, the data flow, and known limitations.
