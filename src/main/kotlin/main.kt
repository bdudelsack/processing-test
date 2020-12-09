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

        size(Math.round(img.width * gridSize + padding * 2), Math.round(img.height * gridSize + padding * 2))
        noLoop()
    }

    override fun draw() {
        beginRecord(PDF, "frame-####.pdf")

        background(color(255, 255, 255));

        repeat(img.height - 1) { y ->
            repeat(img.width - 1) { x ->
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

    fun zigZagShape(w: Float, h: Float, steps: Int, padding: Float = 2f, flip: Boolean = false): PShape =
        createShape().apply {
            val step = (h - 2 * padding) / steps

            beginShape()
            noFill()
            strokeWeight(0.5f);
            strokeJoin(ROUND)

            if (flip) {
                translate(w / 2, h / 2)
                rotate(PI / 2.0f)
                translate(-w / 2, -h / 2)
            }

            repeat(steps) {
                vertex(padding, step * it + padding)
                vertex(w - padding, step * (it + 0.5f) + padding)
            }

            vertex(padding, h - padding)
            endShape()
        }
}

fun main() {
    PApplet.main(Sketch::class.java)
}