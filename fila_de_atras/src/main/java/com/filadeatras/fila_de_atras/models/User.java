package com.filadeatras.fila_de_atras.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonView;
import com.filadeatras.fila_de_atras.models.Message.*;

@Entity
public class User {
	
	public interface UserPost{}
	public interface UserSendMessage extends Message.MessageAddressee{}
	public interface UserReceiveMessage extends Message.MessageSender{}
	public interface ViewUser extends User.UserPost, Post.PostBasic, UserReceiveMessage, UserSendMessage, Comment.CommentId{}
		@JsonView(UserPost.class)
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long id;
		
		@JsonView(UserPost.class)
		@Column(unique=true)
		private String username;
		@JsonView(ViewUser.class)
		@Column(unique=true)
		private String userEmail;
		
		@JsonView(ViewUser.class)
		@ElementCollection(fetch = FetchType.EAGER)
		private List<String> roles;
		
		@ManyToMany
		private List<User> userFollowing;
		
		@ManyToMany(mappedBy="userFollowing",cascade=CascadeType.REMOVE)
		private List<User> userFollowers;
		
		private String userPasswordHash;
		

		@JsonView(ViewUser.class)
		private String userBiography;
		@JsonView(ViewUser.class)
		private String userLocation;
		@JsonView(ViewUser.class)
		private String userLink;
		

		@JsonView(ViewUser.class)
		@OneToMany(mappedBy="commentUser",cascade=CascadeType.REMOVE)
		private List<Comment> userComments;
		
		/**Coge la interfaz MessageId que me proporciona el contenido y el id del mensaje y la interfaz MessageAddressse
		 * que me proporciona al destino del mensaje pero solamente lo proporcionado por la interfaz UserPost
		 * que me proporciona el id y nombre de usuario lo mismo pasa con UserReceiveMessage**/
		//@JsonView(UserSendMessage.class)
		@OneToMany(mappedBy="messageSender",cascade=CascadeType.REMOVE)
		private List<Message> userSentMessages;
		
		//@JsonView(UserReceiveMessage.class)
		@OneToMany(mappedBy="messageAddressee",cascade=CascadeType.REMOVE)
		private List<Message> userReceivedMessages;
		
		@JsonView(ViewUser.class)
		@OneToMany(mappedBy="postAuthor",cascade=CascadeType.REMOVE)
		private List<Post> userPosts;
		@JsonView(ViewUser.class)
		private boolean report;
		
		public User(String nombre, String pass,String email, String... roles){
			username = nombre;
			userEmail = email;
			this.roles = new ArrayList<>(Arrays.asList(roles));
			userPasswordHash= new BCryptPasswordEncoder().encode(pass);
			userComments = new LinkedList<>();
			userSentMessages = new LinkedList<>();
			userReceivedMessages = new LinkedList<>();
			userFollowers = new LinkedList<>();
			userFollowing = new LinkedList<>();
			userPosts = new LinkedList<>();
			report=false;
			userBiography="";
			userLocation="";
			userLink="";
		}

		public User(){}

		public User(String name){
			username = name;
		}
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserPasswordHash() {
			return userPasswordHash;
		}

		public void setUserPasswordHash(String userPasswordHash) {
			this.userPasswordHash = userPasswordHash;
		}
		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		
		public List<Comment> getUserComments() {
			return userComments;
		}
		
		public void setComment(Comment c){
			this.userComments.add(c);
		}
		public void setUserComments(List<Comment> userComments) {
			this.userComments = userComments;
		}
		
		public List<Message> getUserReceivedMessages() {
			return userReceivedMessages;
		}
		
		public void setReceivedMessage(Message m){
			this.userReceivedMessages.add(m);
		}

		public void setUserReceivedMessages(List<Message> userMessages) {
			this.userReceivedMessages = userMessages;
		}
		
		public void setUserSentMessages(List<Message> userMes){
			this.userSentMessages = userMes;
		}
		
		public void setSentMessage(Message m){
			this.userSentMessages.add(m);
		}
		
		public List<Message> getUserSentMessages(){
			return userSentMessages;
		}
		
		public void addFollowing(User following) {
			this.userFollowing.add(following);
		}
		
		public boolean isFollowing(User user){
			for(int i=0; i<this.getUserFollowing().size(); i++){
				if(this.getUserFollowing().get(i).equals(user)){
					return true;
				}
			}
			return false;
			
		}
		
		public List<User> getUserFollowers() {
			return userFollowers;
		}
		
		public void setUserFollowers(List<User> userFollowers) {
			this.userFollowers = userFollowers;
		}

		public List<User> getUserFollowing() {
			return userFollowing;
		}

		public void setUserFollowing(List<User> userFollowing) {
			this.userFollowing = userFollowing;
		}
		
		public List<Post> getUserPosts() {
			return userPosts;
		}
		
		public void addPost(Post e){
			this.userPosts.add(e);
		}

		public void setUserPosts(List<Post> userPosts) {
			this.userPosts = userPosts;
		}

		public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
		
		public void deleteFollowing(User user){
			userFollowing.remove(user);
			
		}
		
		public String getUserBiography() {
			return userBiography;
		}

		public void setUserBiography(String userBiography) {
			this.userBiography = userBiography;
		}

		public String getUserLocation() {
			return userLocation;
		}

		public void setUserLocation(String userLocation) {
			this.userLocation = userLocation;
		}
		
		public boolean equals(User user){
			return this.id==user.id;
		}

		public boolean isReport() {
			return report;
		}

		public void setReport(boolean report) {
			this.report = report;
		}

		public String getUserLink() {
			return userLink;
		}

		public void setUserLink(String userLink) {
			this.userLink = userLink;
		}

		
}