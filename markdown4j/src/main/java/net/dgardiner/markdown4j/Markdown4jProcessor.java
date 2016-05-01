package net.dgardiner.markdown4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.dgardiner.markdown4j.core.Configuration;
import net.dgardiner.markdown4j.core.Configuration.Builder;
import net.dgardiner.markdown4j.core.parser.Processor;
import net.dgardiner.markdown4j.flavours.basic.BasicDecorator;
import net.dgardiner.markdown4j.flavours.base.Decorator;
import net.dgardiner.markdown4j.emitters.CodeBlockEmitter;
import net.dgardiner.markdown4j.plugins.IncludePlugin;
import net.dgardiner.markdown4j.plugins.core.Plugin;
import net.dgardiner.markdown4j.plugins.WebSequencePlugin;
import net.dgardiner.markdown4j.plugins.YumlPlugin;

public class Markdown4jProcessor {
	
	private Builder builder;
	
	private Decorator decorator;
	
	public Markdown4jProcessor() {
		this.builder = builder();
	}
	
	private Builder builder() {
		decorator = new BasicDecorator();

		return Configuration.builder()
			.forceExtentedProfile()
			.registerPlugins(
				new YumlPlugin(),
				new WebSequencePlugin(),
				new IncludePlugin()
			)
			.convertNewline2Br()
			.setDecorator(decorator)
			.setCodeBlockEmitter(new CodeBlockEmitter());
	}

	public Markdown4jProcessor registerPlugins(Plugin... plugins) {
		builder.registerPlugins(plugins);
		return this;
	}

	public Markdown4jProcessor setDecorator(Decorator decorator) {
		this.decorator = decorator;
		builder.setDecorator(decorator);
		return this;
	}

	public Markdown4jProcessor addHtmlAttribute(String name, String value, String ...tags) {
		decorator.addHtmlAttribute(name, value, tags);
		return this;
	}

	public Markdown4jProcessor addStyleClass(String styleClass, String ...tags) {
		decorator.addStyleClass(styleClass, tags);
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
