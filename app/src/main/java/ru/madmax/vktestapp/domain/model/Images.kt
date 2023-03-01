package ru.madmax.vktestapp.domain.model

data class Images(
    val `480w_still`: WStill = WStill(),
    val downsized: Downsized = Downsized(),
    val downsized_large: DownsizedLarge = DownsizedLarge(),
    val downsized_medium: DownsizedMedium = DownsizedMedium(),
    val downsized_small: DownsizedSmall = DownsizedSmall(),
    val downsized_still: DownsizedStill = DownsizedStill(),
    val fixed_height: FixedHeight = FixedHeight(),
    val fixed_height_downsampled: FixedHeightDownsampled = FixedHeightDownsampled(),
    val fixed_height_small: FixedHeightSmall = FixedHeightSmall(),
    val fixed_height_small_still: FixedHeightSmallStill = FixedHeightSmallStill(),
    val fixed_height_still: FixedHeightStill = FixedHeightStill(),
    val fixed_width: FixedWidth = FixedWidth(),
    val fixed_width_downsampled: FixedWidthDownsampled = FixedWidthDownsampled(),
    val fixed_width_small: FixedWidthSmall = FixedWidthSmall(),
    val fixed_width_small_still: FixedWidthSmallStill = FixedWidthSmallStill(),
    val fixed_width_still: FixedWidthStill = FixedWidthStill(),
    val hd: Hd = Hd(),
    val looping: Looping = Looping(),
    val original: Original = Original(),
    val original_mp4: OriginalMp4 = OriginalMp4(),
    val original_still: OriginalStill = OriginalStill(),
    val preview: Preview = Preview(),
    val preview_gif: PreviewGif = PreviewGif(),
    val preview_webp: PreviewWebp = PreviewWebp()
)