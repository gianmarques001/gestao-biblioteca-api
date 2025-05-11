package com.gianmarques001.biblioteca_api.usuario.mapper;


import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioRequestDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioResponseDTO;
import com.gianmarques001.biblioteca_api.permissao.entity.Permissao;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.permissao.repository.PermissaoRepository;
import com.gianmarques001.biblioteca_api.usuario.repository.projection.UsuarioDetailsProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    @Autowired
    private PermissaoRepository permissaoRepository;


    @Mapping(source = "permissao", target = "permissao", qualifiedByName = "toPermissao")
    public abstract Usuario toEntiy(UsuarioRequestDTO usuarioRequestDTO);

    @Mapping(source = "permissao.nome", target = "permissao")
    public abstract UsuarioResponseDTO toDto(Usuario usuario);


    public abstract UsuarioDetailsResponseDTO toDetailsDTO(UsuarioDetailsProjection usuarioDetailsProjection);

    @Named("toPermissao")
    public Permissao toPermissao(String permissao) {
        return permissaoRepository.findByNome(permissao);
    }


}
