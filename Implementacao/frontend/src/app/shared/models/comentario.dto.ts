import { UsuarioDTO } from './usuario.dto';

export interface ComentarioDTO {
  id: number;
  texto: string;
  dataComentario: string; 
  autor: UsuarioDTO;
}
