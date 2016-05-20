/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import raft.postvayler.Persist;
import raft.postvayler.Persistent;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Hakan Eryargi (r a f t)
 * 
 */
@Persistent
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Persist
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

//    @Override
//    public boolean equals(Object obj) {
//    	if (obj == this)
//    		return true;
//    	
//    	return getClass().equals(obj.getClass()) && 
//    }
}
