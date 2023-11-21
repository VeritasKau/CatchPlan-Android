package com.kauproject.kausanhak.presentation.util.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.kauproject.kausanhak.presentation.util.icon.iconpack.Promotion
import com.kauproject.kausanhak.presentation.util.icon.iconpack.Recommend
import kotlin.collections.List as ____KtList

public object IconPack

private var __BottomIcon: ____KtList<ImageVector>? = null

public val IconPack.BottomIcon: ____KtList<ImageVector>
  get() {
    if (__BottomIcon != null) {
      return __BottomIcon!!
    }
    __BottomIcon= listOf(Promotion, Recommend)
    return __BottomIcon!!
  }
