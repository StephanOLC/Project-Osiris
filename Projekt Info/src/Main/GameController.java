package Main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Inputs.KeyboardController;
import Inputs.KeyboardListener;
import Objects.Button;
import Objects.ButtonListener;
import Objects.TextureTest;
import Objects.box;
import Objects.object;

public class GameController implements KeyboardListener{
	
	public Interface inter;
	public KeyboardController keyboardcontroller;
	public List<object> objects = new ArrayList<object>();
	public List<Button> buttons = new ArrayList<Button>();
	
	public GameController(Interface inter){
			
		init(inter);
		createObjects();
		startThreads();
		keyboardcontroller.addkeyboardlistener(this);
		
	}
	
	public void init(Interface inter){
		
		this.inter = inter;
		keyboardcontroller = new KeyboardController(inter);
		new Thread(keyboardcontroller).start();
		
	}
	
	public void createObjects(){
		
		buttons.add(new Button(20, 20, "Graphics/icon.png", "png", "button1", inter));
		objects.add(new box(0,0,1,1,"box1",inter));
		//objects.add(new TextureTest(100, 100,150,150, "Graphics/Unicorn.jpg", "jpg","Texture1", inter));
		objects.add(new box(300,300,"box2",inter));
		objects.add(new box(55,60,"box3",inter));
		objects.add(new TextureTest(-150,100,200,130, "Graphics/Trollface.png", "png","Texture2",inter));
		
	}
	
	private void startThreads(){
		
		for(object obj : objects){
			
			new Thread(obj, obj.getName()).start();
			
		}
		
		for(Button button : buttons){
			
			new Thread(button, button.getName()).start();
			
		}
		
	}
	
	public boolean addButtonListener(ButtonListener buttonListener, String Buttonname){
		
		for(Button button : buttons){
			
			if(button.getName() == Buttonname){
				
				return button.addListener(buttonListener);
				
			}
			
		}
		
		return false;
		
	}
	
	public KeyboardController getKeyboardController(){
		
		return keyboardcontroller;
		
	}

	@Override
	public void action(int EventKey) {
		
		if(EventKey == Keyboard.KEY_ESCAPE){
			
			inter.requestclose();
			
		}
		
	}

}
