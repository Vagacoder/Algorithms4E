import math
import cairo

N = 1000
n = N//10
surface = cairo.ImageSurface(cairo.FORMAT_ARGB32, N, N)
ctx = cairo.Context(surface)

ctx.scale(N, N)

for i in range(n):
    ctx.rectangle(i/n, 1 - i/n, 3/N, 3/N)
    ctx.set_source_rgb(0.9, 0, 0)
    ctx.fill()

    ctx.rectangle(i/n, 1 - (i*i)/n, 3/N, 3/N)
    ctx.set_source_rgb(0, 0.9, 0)
    ctx.fill()

    if i != 0:
        ctx.rectangle(i/n, 1 - (math.log(i, 10)/n), 3/N, 3/N)
        ctx.set_source_rgb(0, 0, 0.9)
        ctx.fill()

surface.write_to_png('StdDrawTester01.png')

