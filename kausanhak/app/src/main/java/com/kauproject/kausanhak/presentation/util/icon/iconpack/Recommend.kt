package com.kauproject.kausanhak.presentation.util.icon.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.kauproject.kausanhak.presentation.util.icon.IconPack

public val IconPack.Recommend: ImageVector
    get() {
        if (_recommend != null) {
            return _recommend!!
        }
        _recommend = Builder(name = "Recommend", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(11.2919f, 3.8426f)
                curveTo(11.5213f, 3.4074f, 11.636f, 3.1897f, 11.7892f, 3.1187f)
                curveTo(11.9226f, 3.0568f, 12.0765f, 3.0568f, 12.21f, 3.1187f)
                curveTo(12.3632f, 3.1897f, 12.4779f, 3.4074f, 12.7073f, 3.8426f)
                lineTo(14.1143f, 6.5121f)
                curveTo(14.2134f, 6.7f, 14.2629f, 6.794f, 14.3367f, 6.8565f)
                curveTo(14.4018f, 6.9118f, 14.48f, 6.9494f, 14.5637f, 6.9659f)
                curveTo(14.6586f, 6.9845f, 14.763f, 6.9647f, 14.9717f, 6.9249f)
                lineTo(17.936f, 6.3606f)
                curveTo(18.4193f, 6.2686f, 18.661f, 6.2226f, 18.8121f, 6.2981f)
                curveTo(18.9437f, 6.3638f, 19.0396f, 6.4841f, 19.0744f, 6.627f)
                curveTo(19.1144f, 6.7911f, 19.0158f, 7.0165f, 18.8185f, 7.4672f)
                lineTo(17.6087f, 10.2317f)
                curveTo(17.5235f, 10.4263f, 17.481f, 10.5236f, 17.478f, 10.6203f)
                curveTo(17.4754f, 10.7056f, 17.4947f, 10.7902f, 17.5341f, 10.866f)
                curveTo(17.5787f, 10.9518f, 17.6593f, 11.021f, 17.8205f, 11.1594f)
                lineTo(20.1099f, 13.1252f)
                curveTo(20.4832f, 13.4457f, 20.6698f, 13.606f, 20.705f, 13.7711f)
                curveTo(20.7357f, 13.915f, 20.7014f, 14.065f, 20.6114f, 14.1813f)
                curveTo(20.508f, 14.3149f, 20.2703f, 14.3783f, 19.7949f, 14.5051f)
                lineTo(16.8793f, 15.2828f)
                curveTo(16.674f, 15.3376f, 16.5714f, 15.365f, 16.494f, 15.423f)
                curveTo(16.4257f, 15.4741f, 16.3716f, 15.542f, 16.3369f, 15.62f)
                curveTo(16.2976f, 15.7084f, 16.2937f, 15.8145f, 16.286f, 16.0268f)
                lineTo(16.1765f, 19.0424f)
                curveTo(16.1587f, 19.5341f, 16.1498f, 19.7799f, 16.0426f, 19.9104f)
                curveTo(15.9492f, 20.0241f, 15.8106f, 20.0909f, 15.6635f, 20.093f)
                curveTo(15.4946f, 20.0954f, 15.2969f, 19.9491f, 14.9013f, 19.6565f)
                lineTo(12.4754f, 17.8619f)
                curveTo(12.3046f, 17.7356f, 12.2192f, 17.6724f, 12.1256f, 17.648f)
                curveTo(12.043f, 17.6265f, 11.9562f, 17.6265f, 11.8736f, 17.648f)
                curveTo(11.78f, 17.6724f, 11.6946f, 17.7356f, 11.5238f, 17.8619f)
                lineTo(9.0979f, 19.6565f)
                curveTo(8.7023f, 19.9491f, 8.5046f, 20.0954f, 8.3357f, 20.093f)
                curveTo(8.1886f, 20.0909f, 8.05f, 20.0241f, 7.9566f, 19.9104f)
                curveTo(7.8494f, 19.7799f, 7.8405f, 19.5341f, 7.8226f, 19.0424f)
                lineTo(7.7132f, 16.0268f)
                curveTo(7.7055f, 15.8145f, 7.7016f, 15.7084f, 7.6623f, 15.62f)
                curveTo(7.6276f, 15.542f, 7.5735f, 15.4741f, 7.5052f, 15.423f)
                curveTo(7.4278f, 15.365f, 7.3251f, 15.3376f, 7.1199f, 15.2828f)
                lineTo(4.2043f, 14.5051f)
                curveTo(3.7289f, 14.3783f, 3.4912f, 14.3149f, 3.3878f, 14.1813f)
                curveTo(3.2978f, 14.065f, 3.2635f, 13.915f, 3.2942f, 13.7711f)
                curveTo(3.3294f, 13.606f, 3.516f, 13.4457f, 3.8893f, 13.1252f)
                lineTo(6.1787f, 11.1594f)
                curveTo(6.3399f, 11.021f, 6.4205f, 10.9518f, 6.4651f, 10.866f)
                curveTo(6.5044f, 10.7902f, 6.5238f, 10.7056f, 6.5212f, 10.6203f)
                curveTo(6.5182f, 10.5236f, 6.4756f, 10.4263f, 6.3905f, 10.2317f)
                lineTo(5.1807f, 7.4672f)
                curveTo(4.9834f, 7.0165f, 4.8848f, 6.7911f, 4.9248f, 6.627f)
                curveTo(4.9596f, 6.4841f, 5.0555f, 6.3638f, 5.1871f, 6.2981f)
                curveTo(5.3382f, 6.2226f, 5.5799f, 6.2686f, 6.0632f, 6.3606f)
                lineTo(9.0275f, 6.9249f)
                curveTo(9.2362f, 6.9647f, 9.3406f, 6.9845f, 9.4354f, 6.9659f)
                curveTo(9.5192f, 6.9494f, 9.5974f, 6.9118f, 9.6625f, 6.8565f)
                curveTo(9.7363f, 6.794f, 9.7858f, 6.7f, 9.8848f, 6.5121f)
                lineTo(11.2919f, 3.8426f)
                close()
            }
        }
        .build()
        return _recommend!!
    }

private var _recommend: ImageVector? = null
