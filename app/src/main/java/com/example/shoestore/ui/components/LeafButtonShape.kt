package com.example.shoestore.ui.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class LeafButtonShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val w = size.width
        val h = size.height
        val r = w * 0.35f

        val path = Path().apply {
            moveTo(0f, r)

            quadraticBezierTo(0f, 0f, r, 0f)
            lineTo(w - r, 0f)
            quadraticBezierTo(w, 0f, w, r)

            lineTo(w, h - r)
            quadraticBezierTo(w, h, w - r, h)
            lineTo(r, h)
            quadraticBezierTo(0f, h, 0f, h - r)

            close()
        }

        return Outline.Generic(path)
    }
}
