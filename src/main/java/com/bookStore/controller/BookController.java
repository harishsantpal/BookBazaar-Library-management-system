package com.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private MyBookListService myBookListService;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/add_new_book")
	public String addNewBook() {
		return "addNewBook";
	}

	@GetMapping("/available_books")
	public ModelAndView availableBooks() {
		List<Book> list = bookService.getAllBooks();

//		ModelAndView modelAndView=new ModelAndView();
//		modelAndView.setViewName("availableBooks");
//		modelAndView.addObject("book", list);
		return new ModelAndView("availableBooks", "book", list);
	}

//	mybooks section
	@GetMapping("/my_books")
	public String getMyBooks(Model model) {

		List<MyBookList> myBookLists = myBookListService.getAllMyBooks();
		model.addAttribute("book", myBookLists);
		return "myBooks";
	}

	@PostMapping("/save")
	public String addBook(@ModelAttribute Book book) {
		/*
		 * if(bindingResult.hasErrors()) {
		 * System.out.println("Result : "+bindingResult); return "addNewBook"; } else {
		 * 
		 * 
		 * bookService.saveBook(book); return "redirect:/available_books"; }
		 */
		bookService.saveBook(book);
		return "redirect:/available_books";
	}
//	

//	@PostMapping("/save")
//	public String addBook(@Valid  Book book, BindingResult bindingResult) {
//
//		if (bindingResult.hasErrors()) {
//			System.out.println("Result : " + bindingResult);
//			return "addNewBook";
//		} else {
//
//			bookService.saveBook(book);
//			return "redirect:/available_books";
//		}
//
////		bookService.saveBook(book);
////		return "redirect:/available_books";
//	}

	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book book = bookService.getBookById(id);
		MyBookList myBookList = new MyBookList(book.getId(), book.getName(), book.getAuthor(), book.getPrice());
		myBookListService.saveMyBook(myBookList);
		return "redirect:/my_books";
	}

//	edit book
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {

		Book book = bookService.getBookById(id);
		model.addAttribute("book", book);
		return "bookEdit";
	}

//	delete book
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id) {

		bookService.deleteById(id);
		return "redirect:/available_books";
	}

}
