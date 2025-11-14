import { Role } from './role.enum';

export interface UsuarioDTO {
  id: number;
  nome: string;
  email: string;
  bio?: string | null;
  fotoPerfil?: number | null;
  role: Role;
}
