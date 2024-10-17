select 
					Cli_For.Codigo, 
					Cli_For.Nome, 
					Cli_For.Cidade, 
					Cli_For.Bairro, 
					Cli_For.Endereco, 
					Cli_For.Fone_1, 
					sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) as Valor, 
					case  
						when (select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) is null 
						then 0 
						else (select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) 
					end as Resgatado, 
					case  
						when sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) -  
						(select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) is null 
						then sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) 
						else sum(Recebe_Dinheiro + Recebe_Cartao_Credito + Recebe_Cartao_Debito + Recebe_Banco_Valor) -  
						(select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = Saidas.Cliente) 
					end as Saldo, 
					(select top 1 Data from Saidas where Cliente = Cli_For.Codigo order by Data desc) as Ultima_Compra 
				 from Cli_For 
					inner join Saidas 
						on Saidas.Cliente = Cli_For.Codigo 
				where  
					Saidas.Efetivada_Financ = 1  
				 group by 
					Saidas.Cliente, 
					Cli_For.Codigo,
					Cli_For.Nome,
					Cli_For.Cidade,
					Cli_For.Fone_1, 
				Cli_For.Bairro, 
					Cli_For.endereco