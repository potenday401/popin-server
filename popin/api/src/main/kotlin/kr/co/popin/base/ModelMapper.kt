package kr.co.popin.base

interface ModelMapper<D, P> {
    fun mapToDomainEntity(model: P): D
    fun mapToPersistenceEntity(model: D): P
}