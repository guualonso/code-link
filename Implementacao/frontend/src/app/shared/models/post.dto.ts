import { UsuarioDTO } from "./usuario.dto";
import { ComentarioDTO } from "./comentario.dto";

export interface PostDTO {
  id: number;
  conteudo: string;
  imagem: string | null;
  dataPostagem: string;
  likes: number;
  autor: UsuarioDTO;
  comunidadeId?: number | null;
  comentarios: ComentarioDTO[];
}
