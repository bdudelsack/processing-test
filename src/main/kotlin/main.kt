import processing.core.PApplet
import processing.core.PImage
import processing.core.PShape


class Sketch : PApplet() {
    lateinit var img: PImage

    val factor = 0.1f
    val gridSize = 15.0f
    val padding = 10f;


    override fun settings() {
        img = loadImage("images/mona.jpg")

        img.resize(Math.round(img.width * factor), Math.round(img.height * factor))
        img.filter(GRAY)

        size(Math.round(img.width * gridSize + padding*2), Math.round(img.height * gridSize + padding*2))
        noLoop()
    }

    override fun draw() {
        beginRecord(PDF, "frame-####.pdf")

        background(color(255, 255, 255));

        for (y in 0..img.height - 1) {
            for (x in 0..img.width - 1) {
                val loc = x + y * img.width

                val pixel = img.pixels[loc]
                val value = 255 - pixel and 0x00FF0000 shr 16
                val flip = x % 2 == y % 2

                val zigZag = zigZagShape(gridSize, gridSize, Math.round(value / 255f * 6), 2f, flip)

                shape(zigZag, x * gridSize + padding, y * gridSize + padding)
            }
        }

        endRecord()
    }

    fun drawZigZag(x: Float, y: Float, w: Float, h: Float, steps: Int, padding: Float = 2f) {
        val step = (h - 2 * padding) / steps

        repeat(steps) {
            line(x + padding, y + step * it + padding, x + w - padding, y + step * (it + 0.5f) + padding)
            line(x + w - padding, y + step * (it + 0.5f) + padding, x + padding, y + step * (it + 1) + padding)
        }
    }

    fun zigZagShape(w: Float, h: Float, steps: Int, padding: Float = 2f, flip: Boolean = false): PShape {
        val shape = createShape()
        val step = (h - 2 * padding) / steps

        shape.beginShape()
        shape.noFill()
        shape.strokeWeight(0.5f);
        shape.strokeJoin(ROUND)

        if(flip) {
            shape.translate(w / 2, h / 2)
            shape.rotate(PI / 2.0f)
            shape.translate(-w / 2, -h / 2)

        }

        repeat(steps) {
            shape.vertex(padding, step * it + padding)
            shape.vertex(w - padding, step * (it + 0.5f) + padding)
        }

        shape.vertex(padding, h - padding)
        shape.endShape()

        return shape
    }
}

fun main() {
    PApplet.main(Sketch::class.java)
}