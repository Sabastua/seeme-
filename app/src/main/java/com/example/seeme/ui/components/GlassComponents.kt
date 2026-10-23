package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 22.dp,
    elevation: Dp = 4.dp,
    backgroundColor: Color = GlassSurfaceWhite,
    borderColor: Color = SmokeWhiteBorder,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .shadow(elevation, RoundedCornerShape(cornerRadius))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(borderColor, SmokeWhiteBorder, borderColor.copy(alpha = 0.5f))
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {
        Column(content = content)
    }
}

@Composable
fun GlassChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = InstagramTextDark
) {
    Box(
        modifier = modifier
            .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
            .background(
                if (isSelected) activeColor else GlassSurfaceWhite,
                CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSelected) activeColor else SmokeWhiteBorder,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = if (isSelected) Color.White else InstagramTextDark
        )
    }
}

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = InstagramTextDark,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(4.dp, CircleShape)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = CircleShape,
        content = content
    )
}
