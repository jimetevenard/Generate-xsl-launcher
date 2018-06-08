package com.jimetevenard.xslt.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jimetevenard.xslt.Driver;
import com.jimetevenard.xslt.GenerationException;
import com.jimetevenard.xslt.launcher.Arguments.Option;


public class Launcher {

	public static void main(String[] args) {

		
		if(args.length < 1 || args[0].equals("--help")){
			help();
		} else {	
			
			System.out.println("gogo");
			
			Arguments arguments = new Arguments(args);
											
			Driver driver = prepareDriver(arguments);
					
			
		
			try {
				driver.launch();
			} catch (GenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	public static Driver prepareDriver(Arguments arguments){
		File scenari = resolve(arguments.getScenariFile());
		
		if(!scenari.isFile()) throwNotFound("scenari", scenari);
		
		Driver driver;
		if(arguments.getOption(Option.CATALOG) != null){
			File catalog = resolve(arguments.getOption(Option.CATALOG));
			if(!catalog.isFile()) throwNotFound("catalog", catalog);
			driver = new Driver(scenari, catalog);
		} else {
			driver = new Driver(scenari);
		}
		
		
		if(arguments.getOption(Option.DEBUG_DIR) != null){
			File debugDir = resolve(arguments.getOption(Option.DEBUG_DIR));
			driver.setDirForIntermediates(debugDir);
		}
		
		if(Boolean.parseBoolean(arguments.getOption(Option.LICENSE))){
			driver.setLicencedSaxon(true);
		}
		
		return driver;
	}
	
	
	
	private static File resolve(String relativePath){
		return new File(new File(System.getProperty("user.dir")), relativePath);
	}
	
	private static void throwNotFound(String label, File f){
		throw new RuntimeException("ERROR, I can't find supplied " + label + " file : " + f.getAbsolutePath());
	}
	
	public static void help(){
		InputStream helpTxt = Launcher.class.getClassLoader().getResourceAsStream("help.md");

		try (BufferedReader rd = new BufferedReader( new InputStreamReader(helpTxt))){
			String line;
			while ((line = rd.readLine()) != null) {		
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println("An exception occured displaying the help file.");
		}
	}

}
