package net.dgardiner.markdown;

import net.dgardiner.markdown.core.Configuration;
import net.dgardiner.markdown.core.Configuration.Builder;
import net.dgardiner.markdown.core.parser.Processor;
import net.dgardiner.markdown.flavours.base.Flavour;
import net.dgardiner.markdown.plugins.core.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class MarkdownProcessor {
	private Builder builder;

	public MarkdownProcessor() {
		this.builder = builder();
	}
	
	private Builder builder() {
		return Configuration.builder();
	}

	//
	// Options
	//

	public MarkdownProcessor setLineBreaks(boolean value) {
		builder.setLineBreaks(value);
		return this;
	}

	public MarkdownProcessor setCompactLists(boolean value) {
		builder.setCompactLists(value);
		return this;
	}

	public MarkdownProcessor setFlavour(Flavour flavour) {
		builder.setFlavour(flavour);
		return this;
	}

	public MarkdownProcessor setIndentCharacter(String value) {
		builder.setIndentCharacter(value);
		return this;
	}

	public MarkdownProcessor setIndentEmptyLines(boolean value) {
		builder.setIndentEmptyLines(value);
		return this;
	}

	public MarkdownProcessor setIndentSize(int value) {
		builder.setIndentSize(value);
		return this;
	}

	public MarkdownProcessor setSafeMode(boolean value) {
		builder.setSafeMode(value);
		return this;
	}

	//
	// Methods
	//

	public String process(File file) throws IOException {
		return Processor.process(file, builder.build());
	}

	public String process(InputStream input) throws IOException {
		return Processor.process(input);
	}

	public String process(Reader reader) throws IOException {
		return Processor.process(reader, builder.build());
	}

	public String process(String input) throws IOException {
		return Processor.process(input, builder.build());
	}

	public MarkdownProcessor registerPlugins(Plugin... plugins) {
		builder.registerPlugins(plugins);
		return this;
	}
}
