package com.brum.mycollection.api.service;

import com.brum.mycollection.api.dto.discogs.DiscogsArtistResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsMasterResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsReleaseResponseDTO;
import com.brum.mycollection.api.dto.discogs.DiscogsSearchResponseDTO;

public interface DiscogsService {
  /**
   * Busca geral no Discogs
   * @param query termo de busca
   * @param type tipo de busca (release, master, artist, label)
   * @param page número da página
   * @param perPage resultados por página
   * @return resposta com resultados paginados
   */
  DiscogsSearchResponseDTO search(String query, String type, int page, int perPage);

  /**
   * Busca releases por nome do artista
   */
  DiscogsSearchResponseDTO searchByArtist(String artistName, int page, int perPage);

  /**
   * Busca releases por título do álbum
   */
  DiscogsSearchResponseDTO searchByAlbum(String albumTitle, int page, int perPage);

  /**
   * Obtém detalhes completos de um release específico
   * @param releaseId ID do release no Discogs
   * @return detalhes do release (tracklist, créditos, imagens, etc.)
   */
  DiscogsReleaseResponseDTO getReleaseDetails(Integer releaseId);

  /**
   * Obtém detalhes de um artista específico
   * @param artistId ID do artista no Discogs
   * @return detalhes do artista (nome, profile, imagens, etc.)
   */
  DiscogsArtistResponseDTO getArtistDetails(Integer artistId);

  /**
   * Obtém detalhes de um master release (álbum original)
   * @param masterId ID do master release no Discogs
   * @return detalhes do master (título, ano original, etc.)
   */
  DiscogsMasterResponseDTO getMasterDetails(Integer masterId);

}
