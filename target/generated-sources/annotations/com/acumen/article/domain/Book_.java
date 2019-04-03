package com.acumen.article.domain;

import com.acumen.article.domain.enumeration.BookCategory;
import com.acumen.article.domain.enumeration.BookFormat;
import com.acumen.article.domain.enumeration.Language;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, Integer> inStockNumber;
	public static volatile SingularAttribute<Book, byte[]> bookImage;
	public static volatile SingularAttribute<Book, String> author;
	public static volatile SingularAttribute<Book, Integer> isbn;
	public static volatile SingularAttribute<Book, BookFormat> format;
	public static volatile SingularAttribute<Book, String> bookImageContentType;
	public static volatile SingularAttribute<Book, Boolean> active;
	public static volatile SingularAttribute<Book, String> description;
	public static volatile SingularAttribute<Book, Language> language;
	public static volatile SingularAttribute<Book, String> title;
	public static volatile SingularAttribute<Book, Integer> numberOfPages;
	public static volatile SingularAttribute<Book, Double> shippingWeight;
	public static volatile SingularAttribute<Book, String> publisher;
	public static volatile SingularAttribute<Book, Long> id;
	public static volatile SingularAttribute<Book, BookCategory> category;
	public static volatile SingularAttribute<Book, String> publicationDate;
	public static volatile SingularAttribute<Book, Double> ourPrice;
	public static volatile SingularAttribute<Book, Double> lastPrice;

}

