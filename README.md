# markdown-java

[![][badge-bintray]][badge-bintray-href] [![][badge-license]](LICENSE.md) [![][badge-travis]][badge-travis-href]

## Download

**Gradle**
```gradle
repositories {
    maven { url "http://dl.bintray.com/fuzeman/maven" }
}

dependencies { 
    compile 'net.dgardiner.markdown:markdown:1.0.0'
}
```

## Usage

**Basic**
```java
MarkdownProcessor processor = new MarkdownProcessor();

String html = processor.process('## Markdown');
```

**Github Flavoured Markdown**
```java
MarkdownProcessor processor = new MarkdownProcessor();
processor.setFlavour(new GithubFlavour());

String html = processor.process(':sparkles: :camel: :boom:');
```


[badge-bintray]: https://img.shields.io/bintray/v/fuzeman/maven/markdown-java.svg?maxAge=2592000
[badge-bintray-href]: https://bintray.com/fuzeman/maven/markdown-java
[badge-license]: https://img.shields.io/badge/license-BSD%203--Clause-brightgreen.svg?style=flat-square
[badge-travis]: https://img.shields.io/travis/fuzeman/markdown-java.svg?style=flat-square&maxAge=2592000
[badge-travis-href]: https://travis-ci.org/fuzeman/markdown-java