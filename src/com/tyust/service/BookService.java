package com.tyust.service;

import com.tyust.entity.Book;
import com.tyust.pager.PageBean;

public interface BookService {
	
	public int findBookCountByCategory(String cid);
	
	public Book load(String bid);
	
	public PageBean<Book> findByCategory(String cid, int pc);
	
	public PageBean<Book> findByBname(String bname, int pc);
	
	public PageBean<Book> findByAuthor(String author, int pc);
	
	public PageBean<Book> findByPress(String press, int pc);
	
	public PageBean<Book> findByConbination(Book criteria, int pc);
	
	public void addBook(Book book);
	
	public void edit(Book book);
	
	public void delete(String bid);
	
}
