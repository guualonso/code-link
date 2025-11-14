export interface UsuarioCreateDTO {
  nome: string;
  email: string;
  senha: string;
  bio?: string | null;
  fotoPerfil?: number | null;
}
