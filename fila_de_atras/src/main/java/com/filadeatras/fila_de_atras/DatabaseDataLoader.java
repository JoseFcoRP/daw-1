package com.filadeatras.fila_de_atras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filadeatras.fila_de_atras.models.Comment;
import com.filadeatras.fila_de_atras.models.Message;
import com.filadeatras.fila_de_atras.models.Post;
import com.filadeatras.fila_de_atras.models.User;
import com.filadeatras.fila_de_atras.services.CommentService;
import com.filadeatras.fila_de_atras.services.MessageService;
import com.filadeatras.fila_de_atras.services.PostService;
import com.filadeatras.fila_de_atras.services.UserService;


@Component
public class DatabaseDataLoader {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private PostService postService;
	
	@Autowired
	UserComponent userComponent;

	    @PostConstruct
	    private void initDatabase() {
	    	
	    	User u1 = new User("Pepe", "pass", "pepe@a.aa" ,"ROLE_USER");
			User u2 = new User("Sara", "word", "sara@a.aa" ,"ROLE_USER","ROLE_ADMIN");
			userService.save(u1);
			userService.save(u2);
			u1.addFollowing(u2); // u1 sigue a u2, añadir en la lista de seguidos en u1 a u2
			userService.save(u1);
			userService.save(u2);
			Message userServiceuserService = new Message("Este es el contenido del mensaje", u1,u2);
			messageService.save(userServiceuserService);
			Post p1 = new Post("El titulo del post", u2, "savage");
			Post p2 = new Post("1", u2, "wtf");
			Post p3 =new Post("2", u2, "funny");
			p2.setPostUpVotes(15);
			p3.setPostUpVotes(30);
			p3.setPostDate(LocalDateTime.now().minusMonths(2).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.MEDIUM)));
			p3.setMonth(LocalDateTime.now().minusMonths(2).toString());
			p3.setPostWeek(3);
			postService.save(p1);
			postService.save(p2);
			postService.save(p3);
			Comment c1 = new Comment("Esto es el contenido de un comentario",u1,p1);
			commentService.save(c1);
			
			
			
			
	    	int n=10;
	    	User newUs[] = new User[n];
	    	for(int i=0;i<5;i++){
	    		newUs[i] = (new User("user"+i, "pass"+i, "user"+i+".a@a.aa", "ROLE_USER"));
	    		userService.save(newUs[i]);
	    		if(i>0){
	    			Message m = new Message("Texto de prueba "+i, newUs[0], newUs[1]);
	    			messageService.save(m);
	    			userService.save(newUs[0]);
	    		}
	    		Message m = new Message("Texto de prueba aux "+i, newUs[i], newUs[1]);
	    		messageService.save(m);
	    	}
	    	//No se por que, pero añadir following dentro del bucle no funciona.
			newUs[0].addFollowing(newUs[1]);
			newUs[0].addFollowing(newUs[2]);
			newUs[0].addFollowing(newUs[3]);
			newUs[0].addFollowing(newUs[4]);
			userService.save(newUs[0]);
	    	for(int i=0; i<11; i++){
				if(i<4){
					Post p =new Post("Prueba "+i, newUs[i], "funny");
					postService.save(p);
				}
				else if(i<8){
					Post p =new Post("Prueba "+i, u1, "wtf");
					postService.save(p);
				}
				else{
					Post p =new Post("Prueba "+i, u2, "animals");
					postService.save(p);
				}
				
			}
	    	Message m = new Message("Texto de prueba eliminado", newUs[0], newUs[1]);
	    	Message m2 = new Message("Texto de prueba leido", newUs[3], newUs[1]);
	    	m2.setMessageNew(false);
	    	m.setMessageDeleted(true);
	    	messageService.save(m);
	    	messageService.save(m2);
			userService.save(new User("admin", "adminpass", "admin.a@a.aa","ROLE_USER","ROLE_ADMIN"));

	    }
	    
}
