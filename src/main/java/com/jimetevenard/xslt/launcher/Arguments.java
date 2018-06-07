package com.jimetevenard.xslt.launcher;

import java.util.HashMap;
import java.util.Map;

public class Arguments {

	private Map<Option, String> options;
	private String scenariFile;

	private Option nextOption;

	public Arguments(String[] args) {
		options = new HashMap<>();
		
		for (String arg : args) {
			if (arg.startsWith("-")) {
				takeOption(arg);
			} else {
				takeValue(arg);
			}
		}

	}

	private void takeValue(String arg) {
		if(this.nextOption != null){
			options.put(this.nextOption, arg);
			this.nextOption = null;
		} else {
			this.scenariFile = arg;
		}

	}

	private void takeOption(String arg) {
		switch (arg) {
		case "-d":
		case "--debug-dir":
			this.nextOption = Option.DEBUG_DIR;
			break;
		
		case "-c":
		case "--catalog":
			this.nextOption = Option.CATALOG;
			break;
		
		case "-l":
		case "--license":
			this.nextOption = null; // Pas d'argument !!
			options.put(Option.LICENSE, Boolean.toString(true));
			break;

		}

	}

	public enum Option {
		DEBUG_DIR, CATALOG, LICENSE
	}

	public String getOption(Option op){
		return this.options.get(op);
	}

	public String getScenariFile() {
		return scenariFile;
	}

	@Override
	public String toString() {
		return "Arguments [options=" + options + ", scenariFile=" + scenariFile + "]";
	}
	
	
	

}
