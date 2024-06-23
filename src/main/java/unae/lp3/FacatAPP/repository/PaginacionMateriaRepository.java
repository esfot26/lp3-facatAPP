package unae.lp3.FacatAPP.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import unae.lp3.FacatAPP.model.Materia;

public interface PaginacionMateriaRepository extends PagingAndSortingRepository<Materia, Integer> {

}
