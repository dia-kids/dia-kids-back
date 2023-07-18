/**
 * Created by Dawid Stankiewicz on 18.07.2016
 */
package com.dia.kids.forum.post;

import com.dia.kids.forum.topic.Topic;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dia.kids.forum.user.User;


public interface PostRepository extends JpaRepository<Post, Integer> {
    
    Set<Post> findByUser(User user);
    
    Set<Post> findByTopic(Topic topic);
    
    Set<Post> findAllByOrderByCreationDateDesc();
    
    Set<Post> findTop5ByOrderByCreationDateDesc();
}
