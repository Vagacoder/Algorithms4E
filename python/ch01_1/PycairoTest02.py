import math
import cairo

WIDTH, HEIGHT = 256, 256

surface = cairo.ImageSurface(cairo.FORMAT_ARGB32, WIDTH, HEIGHT)
ctx = cairo.Context(surface)

# Normalizing the canvas
ctx.scale(WIDTH, HEIGHT)

# Set linear gradient
pat = cairo.LinearGradient(0.0, 0.0, 0.0, 1.0)
pat.add_color_stop_rgba(0.75, 0.7, 0, 0, 0.75)
pat.add_color_stop_rgba(0, 0.9, 0.75, 0.2, 1)

# Draw rectangle filled with linear gradient
ctx.rectangle(0,0,0.5,0.9)
ctx.set_source(pat)
ctx.fill()
# ctx.set_line_width(0.02)
# ctx.stroke()

# Changing the current transformation matrix
ctx.translate(0.1, 0.1)
ctx.move_to(0,0)

# Draw Arc
ctx.arc(0.2, 0.1, 0.1, -math.pi /2, 0)
ctx.line_to(0.5, 0.1)

# Draw Curve
ctx.curve_to(0.5, 0.2, 0.5, 0.4, 0.2, 0.8)
ctx.close_path()

ctx.set_source_rgb(0.3, 0.2, 0.5)
ctx.set_line_width(0.02)
ctx.stroke()

surface.write_to_png("PycairoTest02.png")
