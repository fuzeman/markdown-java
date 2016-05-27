## 1.1.0

#### General

**Added**

 - `MarkdownProcessor.setLineBreaks(boolean)`

**Changed**

 - Improved `BoldToken` and `ItalicToken` matching
 - Unknown html tags are now escaped when safe mode is enabled
 - Safe mode is now enabled by default
 - Renamed `MarkdownProcessor.enableSafeMode()` to `MarkdownProcessor.setSafeMode(boolean)`


**Fixed**

 - `CodeBlock` was being matched incorrectly


#### BasicFlavour

**Added**

 - `GreaterThanToken` and `LessThanToken` plugins


#### ExtendedFlavour

**Changed**

 - Removed `QuoteToken` and `EllipsisToken` plugins


#### GithubFlavour

**Added**

 - `AutoLinkToken` plugin


**Changed**

 - Improved performance of `GithubEmojiToken` matching


## 1.0.0

Initial release