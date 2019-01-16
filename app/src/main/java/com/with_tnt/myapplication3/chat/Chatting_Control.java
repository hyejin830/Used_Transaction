package com.with_tnt.myapplication3.chat;

public class Chatting_Control
{
	String numberOfInquiry;
	int align;

	public Chatting_Control(String numberOfInquiry, int align)
	{
		this.align = align;
		this.numberOfInquiry = numberOfInquiry;
	}

	public String getNumberOfInquiry()
	{
		return numberOfInquiry;
	}
	public int getAlign()
	{
		return align;
	}
}
