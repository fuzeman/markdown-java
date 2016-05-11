package net.dgardiner.markdown4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Configuration.Builder;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.core.base.Flavour;
import net.dgardiner.markdown4j.plugins.IncludePlugin;
import net.dgardiner.markdown4j.plugins.core.Plugin;
import net.dgardiner.markdown4j.plugins.WebSequencePlugin;
import net.dgardiner.markdown4j.plugins.YumlPlugin;

public class Markdown4jProcessor {
	
	private Builder builder;
	private Flavour flavour;
	
	public Markdown4jProcessor() {
		this.builder = builder();
	}
	
	private Builder builder() {
		return Configuration.builder()
			.forceExtentedProfile()
			.registerPlugins(
				new YumlPlugin(),
				new WebSequencePlugin(),
				new IncludePlugin()
			)
			.convertNewline2Br();
	}

	public Markdown4jProcessor registerPlugins(Plugin... plugins) {
		builder.registerPlugins(plugins);
		return this;
	}

	public Markdown4jProcessor setFlavour(Flavour flavour) {
		this.flavour = flavour;
		builder.setFlavour(flavour);
		return this;
	}

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
}
