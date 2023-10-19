package com.visioncameraocr

import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import android.media.Image
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.mrousavy.camera.frameprocessor.Frame
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin
import com.mrousavy.camera.types.Orientation

class OCRFrameProcessorPlugin: FrameProcessorPlugin() {

    private fun getBlockArray(blocks: List<Text.TextBlock>): List<Map<String, Any?>> {
        return blocks.map { block ->
            mapOf(
                "text" to block.text,
                "recognizedLanguages" to getRecognizedLanguages(block.recognizedLanguage),
                "cornerPoints" to getCornerPoints(block.cornerPoints ?: emptyArray()),
                "frame" to getFrame(block.boundingBox),
                "lines" to getLineList(block.lines)
            )
        }
    }

    private fun getLineList(lines: List<Text.Line>): List<Map<String, Any?>> {
        return lines.map { line ->
            mapOf(
                "text" to line.text,
                "recognizedLanguages" to getRecognizedLanguages(line.recognizedLanguage),
                "cornerPoints" to getCornerPoints(line.cornerPoints ?: emptyArray()),
                "frame" to getFrame(line.boundingBox),
                "elements" to getElementList(line.elements)
            )
        }
    }

    private fun getElementList(elements: List<Text.Element>): List<Map<String, Any?>> {
        return elements.map { element ->
            mapOf(
                "text" to element.text,
                "cornerPoints" to getCornerPoints(element.cornerPoints ?: emptyArray()),
                "frame" to getFrame(element.boundingBox)
            )
        }
    }

    private fun getRecognizedLanguages(recognizedLanguage: String): List<String> {
        return listOf(recognizedLanguage)
    }

    private fun getCornerPoints(points: Array<Point>): List<Map<String, Int>> {
        return points.map { point ->
            mapOf(
                "x" to point.x,
                "y" to point.y
            )
        }
    }

    private fun getFrame(boundingBox: Rect?): Map<String, Any?> {
        return boundingBox?.let {
            mapOf(
                "x" to it.exactCenterX().toDouble(),
                "y" to it.exactCenterY().toDouble(),
                "width" to it.width(),
                "height" to it.height(),
                "boundingCenterX" to it.centerX(),
                "boundingCenterY" to it.centerY()
            )
        } ?: emptyMap()
    }

    override fun callback(frame: Frame, arguments: Map<String, Any>?): Any? {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        @SuppressLint("UnsafeOptInUsageError")
        val mediaImage: Image = frame.image ?: return null

        val rotationDegrees = Orientation.fromUnionValue(frame.orientation)?.toDegrees() ?: Orientation.PORTRAIT.toDegrees()
        val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)
        val task: Task<Text> = recognizer.process(image)
        try {
            val text: Text = Tasks.await<Text>(task)
            val result = mapOf(
                "text" to text.text,
                "blocks" to getBlockArray(text.textBlocks)
            )
            return mapOf("result" to result)
        } catch (e: Exception) {
            return null
        }
    }
}
