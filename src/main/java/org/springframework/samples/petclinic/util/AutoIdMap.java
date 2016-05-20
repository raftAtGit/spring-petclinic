package org.springframework.samples.petclinic.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.samples.petclinic.model.BaseEntity;

import raft.postvayler.Persist;
import raft.postvayler.Persistent;

/**
 * Extension to Java Map for auto assigning keys  
 *  
 * @author Hakan Eryargi (r a f t)
 *  */
public interface AutoIdMap<V extends BaseEntity> extends Map<Integer, V> {

	/** Assigns an id to value and puts into map */
	public void put(V value);
	
	@Persistent
	public static class Impl<V extends BaseEntity> extends LinkedHashMap<Integer, V> implements AutoIdMap<V>  {
		private static final long serialVersionUID = 1L;

		private int lastId;

		@Persist
		@Override
		public void put(V value) {
			synchronized (this) {
				int id = lastId++;
				value.setId(id);
			}
		}
		
		// override some methods to make them persistent
		@Persist
		@Override
		public V put(Integer key, V value) {
			return super.put(key, value);
		}
		@Persist
		@Override
		public void putAll(Map<? extends Integer, ? extends V> m) {
			super.putAll(m);
		}
		@Persist
		@Override
		public V putIfAbsent(Integer key, V value) {
			return super.putIfAbsent(key, value);
		}
		@Persist
		@Override
		public boolean replace(Integer key, V oldValue, V newValue) {
			return super.replace(key, oldValue, newValue);
		}
		@Persist
		@Override
		public V remove(Object key) {
			return super.remove(key);
		}
		@Persist
		@Override
		public void clear() {
			super.clear();
		}
		
		// TODO we should also wrap keys() and values() to make the fully persistent too. 
		// or return unmodifiable keys() and values()   
	}
}
