package com.gianmarques001.biblioteca_api.livro.mapper;


import com.gianmarques001.biblioteca_api.livro.dto.LivroDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.livro.dto.LivroResponseDTO;
import com.gianmarques001.biblioteca_api.livro.dto.LivroRequestDTO;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LivroMapper {


    Livro toEntity(LivroRequestDTO livroRequestDto);

    LivroDetailsResponseDTO toDTO(Livro livro);

    LivroResponseDTO toPesquisaDTO(Livro livro);
}
