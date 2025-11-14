export interface PostCreateDTO {
  conteudo: string;
  imagem?: string | null;
  autorId: number;
  comunidadeId?: number | null;
}
