/**
 * Created by Dawid Stankiewicz on 17.07.2016
 */
package com.dia.kids.forum.section;

import java.util.List;


public interface SectionService {
    
    List<Section> findAll();
    
    Section findOne(int id);
    
    Section findByName(String name);
    
    Section save(Section section);
    
    void delete(int id);
    
    void delete(Section section);
    
}
