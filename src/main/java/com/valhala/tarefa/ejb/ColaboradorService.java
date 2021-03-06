package com.valhala.tarefa.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import com.valhala.tarefa.dao.api.ColaboradorDao;
import com.valhala.tarefa.exceptions.ConsultaSemRetornoException;
import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;
import com.valhala.tarefa.exceptions.ServiceException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.qualifiers.Auditavel;
import com.valhala.tarefa.util.Copiador;

/**
 * EJB responsavel pela regra de negócio relacionada a Colaborador
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
@Auditavel
@Stateless @TransactionManagement(TransactionManagementType.CONTAINER)
public class ColaboradorService {
	
	@Inject
	private ColaboradorDao colaboradorDao;
	
	/**
	 * Método utilizado para realizar a ação de cadastro de colaborador.
	 * @param colaborador
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cadastrarColaborador(Colaborador colaborador) {
		this.colaboradorDao.persistir(colaborador);
	} // fim do método cadastrarColaborador
	
	/**
	 * Método utilizado para realizar a ação de edição de dados de colaborador.
	 * @param colaborador
	 * @throws ServiceException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void editarColaborador(Colaborador colaborador) throws ServiceException {
		Colaborador colaboradorPersistente;
		try {
			colaboradorPersistente = this.colaboradorDao.buscarPorId(colaborador.getId());
			Copiador.copiar(Colaborador.class, colaboradorPersistente, colaborador);
			this.colaboradorDao.atualizar(colaboradorPersistente);
		} catch (ConsultaSemRetornoException | CopiaDePropriedadesException e) {
			throw new ServiceException(e.getMessage(), e);
		} // fim do bloco try/catch
	} // fim do método editarColaborador
	
	/**
	 * Método utilizado para realizar a ação de remoção de colaborador.
	 * @param id
	 * @throws ServiceException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removerColaborador(Serializable id) throws ServiceException {
		try {
			this.colaboradorDao.remover(this.colaboradorDao.buscarPorId(id));
		} catch (ConsultaSemRetornoException e) {
			throw new ServiceException(e.getMessage(), e);
		} // fim do bloco try/catch
	} // fim do método removerColaborador
	
	/**
	 * Método utilizado para ralizar a ação de buscar colaboradores por id.
	 * @param id
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Colaborador buscarPorId(Serializable id) throws ConsultaSemRetornoException {
		return this.colaboradorDao.buscarPorId(id);
	} // fim do método buscarPorId
	
	/**
	 * Método utilizado para realizar a ação de buscar todos os colaborador cadastrados.
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Colaborador> buscarTodosColaboradores() throws ConsultaSemRetornoException {
		return this.colaboradorDao.listarTudo();
	} // fim do método buscarTodosColaboradores
	
	/**
	 * Método utilizado para realizar a ação de buscar um colaborador pela sua matricula.
	 * @param matricula
	 * @return
	 * @throws ConsultaSemRetornoException
	 */
	@Auditavel
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Colaborador buscarPorMatricula(String matricula) throws ConsultaSemRetornoException {
		return this.colaboradorDao.buscarPorMatricula(matricula);
	} // fim do método buscarPorMatricula

} // fim da classe ColaboradorService
