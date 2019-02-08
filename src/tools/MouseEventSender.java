package tools;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public interface MouseEventSender{


	public void add(MouseListener el);
	public void remove(MouseListener el);
}