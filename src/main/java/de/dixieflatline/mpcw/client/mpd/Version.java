/***************************************************************************
    begin........: September 2018
    copyright....: Sebastian Fedrau
    email........: sebastian.fedrau@gmail.com
 ***************************************************************************/

/***************************************************************************
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License v3 as published by
    the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    General Public License v3 for more details.
 ***************************************************************************/
package de.dixieflatline.mpcw.client.mpd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version
{
	private final int major;
	private final int minor;
	private final int revision;
	
	public Version(int major, int minor, int revision)
	{
		this.major = major;
		this.minor = minor;
		this.revision = revision;
	}
	
	public static final Version parse(String version) throws InvalidFormatException
	{
		Pattern pattern = Pattern.compile("^OK MPD (\\d+)\\.(\\d+)\\.(\\d+)$");
		Matcher matcher = pattern.matcher(version);
		
		if(matcher.find())
		{
			int major = Integer.parseInt(matcher.group(1));
			int minor = Integer.parseInt(matcher.group(2));
			int revision = Integer.parseInt(matcher.group(3));
			
			return new Version(major, minor, revision);
		}
		
		throw new InvalidFormatException(version);
	}

	@Override
	public String toString()
	{
		return String.format("%d.%d.%d", major, minor, revision);
	}
}