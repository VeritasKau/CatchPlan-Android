package com.kauproject.kausanhak.presentation.util.icon.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.presentation.util.icon.IconPack

public val IconPack.Promotion: ImageVector
    get() {
        if (_promotion != null) {
            return _promotion!!
        }
        _promotion = Builder(name = "Promotion", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF0F0F0F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(20.0f, 1.0f)
                curveTo(21.6569f, 1.0f, 23.0f, 2.3431f, 23.0f, 4.0f)
                verticalLineTo(14.0f)
                curveTo(23.0f, 15.6569f, 21.6569f, 17.0f, 20.0f, 17.0f)
                horizontalLineTo(13.562f)
                lineTo(18.6402f, 21.2318f)
                curveTo(19.0645f, 21.5853f, 19.1218f, 22.2159f, 18.7682f, 22.6402f)
                curveTo(18.4147f, 23.0645f, 17.7841f, 23.1218f, 17.3598f, 22.7682f)
                lineTo(13.0f, 19.135f)
                verticalLineTo(22.0f)
                curveTo(13.0f, 22.5523f, 12.5523f, 23.0f, 12.0f, 23.0f)
                curveTo(11.4477f, 23.0f, 11.0f, 22.5523f, 11.0f, 22.0f)
                verticalLineTo(19.135f)
                lineTo(6.6402f, 22.7682f)
                curveTo(6.2159f, 23.1218f, 5.5853f, 23.0645f, 5.2318f, 22.6402f)
                curveTo(4.8782f, 22.2159f, 4.9355f, 21.5853f, 5.3598f, 21.2318f)
                lineTo(10.438f, 17.0f)
                horizontalLineTo(4.0f)
                curveTo(2.3431f, 17.0f, 1.0f, 15.6569f, 1.0f, 14.0f)
                verticalLineTo(4.0f)
                curveTo(1.0f, 2.3431f, 2.3431f, 1.0f, 4.0f, 1.0f)
                horizontalLineTo(20.0f)
                close()
                moveTo(20.0f, 3.0f)
                curveTo(20.5523f, 3.0f, 21.0f, 3.4477f, 21.0f, 4.0f)
                verticalLineTo(14.0f)
                curveTo(21.0f, 14.5523f, 20.5523f, 15.0f, 20.0f, 15.0f)
                horizontalLineTo(4.0f)
                curveTo(3.4477f, 15.0f, 3.0f, 14.5523f, 3.0f, 14.0f)
                verticalLineTo(4.0f)
                curveTo(3.0f, 3.4477f, 3.4477f, 3.0f, 4.0f, 3.0f)
                horizontalLineTo(20.0f)
                close()
            }
        }
        .build()
        return _promotion!!
    }

private var _promotion: ImageVector? = null
