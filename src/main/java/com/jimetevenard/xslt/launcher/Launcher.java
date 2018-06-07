package com.jimetevenard.xslt.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jimetevenard.xslt.Driver;
import com.jimetevenard.xslt.launcher.Arguments.Option;


public class Launcher {

	public static void main(String[] args) {

		
		if(args.length < 1 || args[0].equals("--help")){
			help();
		} else {	
			
			Arguments arguments = new Arguments(args);
						
			File scenari = new File(new File(System.getProperty("user.dir")), arguments.getScenariFile());
			
			if(!scenari.isFile()) throw new RuntimeException("Mhh. I can't find scenari file " + scenari.getAbsolutePath());
			
			Driver driver;
			if(arguments.getOption(Option.CATALOG) != null){
				File catalog = new File(new File(System.getProperty("user.dir")), arguments.getOption(Option.CATALOG));
				if(!catalog.isFile()) throw new RuntimeException("Mhh. I can't find catalog file " + catalog.getAbsolutePath());
				driver = new Driver(scenari, catalog);
			} else {
				driver = new Driver(scenari);
			}
			
			
			if(arguments.getOption(Option.DEBUG_DIR) != null){
				File debugDir = new File(new File(System.getProperty("user.dir")), arguments.getOption(Option.CATALOG));
				driver.setDirForIntermediates(debugDir);
			}
			
			if(Boolean.parseBoolean(arguments.getOption(Option.LICENSE))){
				driver.setLicencedSaxon(true);
			}
		}

	}
	
	public static void help(){
		InputStream helpTxt = Launcher.class.getClassLoader().getResourceAsStream("help.txt");

		try (BufferedReader rd = new BufferedReader( new InputStreamReader(helpTxt))){
			String line;
			while ((line = rd.readLine()) != null) {		
				System.out.println(line);
			}
		} catch (IOException e) {
			
		}
	}

}
