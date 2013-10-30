/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonas
 */
public class BookItem {

    int _code;
    long _position;
    String _author;
    String _title;
    String _edition;
    int _year;

    public BookItem(int cod, long position) {
        _code = cod;
        _position = position;
    }
    
    public BookItem(String line) {
    	this.setCode(Integer.parseInt(line.substring(0, 7)));
    	this.setTitle(line.substring(7, 52));
    	this.setAuthor(line.substring(52, 82));
    	this.setEdition(line.substring(82, 83));
    	if(!line.substring(83, 87).trim().isEmpty()){
    		this.setYear(Integer.parseInt(line.substring(83, 87)));
    	}
    }
    
    public BookItem() {
        
    }
    
    public String print()
    {
        return (_code + " | " + _title + " | " + _author + " | " + _edition + " | " + _year); // cod tit auto edi ano
    }
    
    public String getAuthor() {
        return _author;
    }

    public void setAuthor(String _author) {
        this._author = _author;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getEdition() {
        return _edition;
    }

    public void setEdition(String _edition) {
        this._edition = _edition;
    }

    public int getYear() {
        return _year;
    }

    public void setYear(int _year) {
        this._year = _year;
    }

    public int getCode() {
        return _code;
    }

    public void setCode(int _code) {
        this._code = _code;
    }

    public long getPosition() {
        return _position;
    }

    public void setPosition(long _position) {
        this._position = _position;
    }

}
