package com.jothi.jdbc.pojo;

public class Book {
	private int bookid ;
	private String bookname;
	private String author;
	private String publication;
	private double cost; 
	private int edition;
	
	
	
	public Book(int bookid, String bookname, String author, String publication, double cost, int edition) {
		super();
		this.bookid = bookid;
		this.bookname = bookname;
		this.author = author;
		this.publication = publication;
		this.cost = cost;
		this.edition = edition;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
}
