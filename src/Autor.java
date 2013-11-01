import java.io.Serializable;
import java.util.LinkedList;


public class Autor implements Serializable {
	String nome;
	LinkedList<String> enderecos =  new LinkedList<String>();

	public Autor(String nome)
	{
		this.nome = nome;
	}
	
	public void IncluiEndereco(String end)
	{
		enderecos.add(end);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LinkedList<String> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(LinkedList<String> enderecos) {
		this.enderecos = enderecos;
	}
	
	@Override
    public boolean equals(Object obj)
    {
	  	if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Autor other = (Autor) obj;
        return nome.equals(other.nome);
    }
	  
}
