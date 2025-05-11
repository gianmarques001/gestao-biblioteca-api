package com.gianmarques001.biblioteca_api.emprestimo.mapper;

import com.gianmarques001.biblioteca_api.emprestimo.dto.EmprestimoDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.emprestimo.dto.EmprestimoListResponseDTO;
import com.gianmarques001.biblioteca_api.emprestimo.dto.EmprestimoResponseDTO;
import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(source = "usuario.nome", target = "nome")
    @Mapping(source = "livro.titulo", target = "titulo")
        EmprestimoListResponseDTO toEmprestimoListResponseDTO(Emprestimo emprestimo);


    @Mapping(source = "usuario.nome", target = "nome")
    @Mapping(source = "livro.titulo", target = "titulo")
    EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo);

    @Mapping(source = "livro.titulo", target = "titulo")
    EmprestimoDetailsResponseDTO toEmprestimoDetailsResponseDTO(Emprestimo emprestimo);

}
