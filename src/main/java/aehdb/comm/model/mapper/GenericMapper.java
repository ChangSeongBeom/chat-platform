package aehdb.comm.model.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;


public interface GenericMapper <D,E>{
	D toDto(E e);
	E toEntity(D d);
	
	List<D> toDto(List<E> e);
	List<E> toEntity(List<D> d);

}
