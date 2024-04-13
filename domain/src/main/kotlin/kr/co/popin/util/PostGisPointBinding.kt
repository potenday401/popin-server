package kr.co.popin.util

import org.jooq.*
import org.jooq.impl.DSL
import org.locationtech.jts.geom.Point

class PostGisPointBinding : Binding<Any, Point> {

    private val converter = PostGisPointConverter()
    override fun converter(): Converter<Any, Point> = converter

    override fun sql(ctx: BindingSQLContext<Point>?) {
        ctx?.render()?.visit(DSL.sql("?::geometry"))
    }

    override fun get(ctx: BindingGetStatementContext<Point>?) {
        ctx?.convert(converter())?.value(ctx.statement().getObject(ctx.index()))
    }

    override fun get(ctx: BindingGetResultSetContext<Point>?) {
        ctx?.convert(converter())?.value(ctx.resultSet().getObject(ctx.index()))
    }

    override fun set(ctx: BindingSetStatementContext<Point>?) {
        ctx?.statement()?.setObject(ctx.index(), ctx.convert(converter()).value())
    }

    override fun set(ctx: BindingSetSQLOutputContext<Point>?) {
        throw UnsupportedOperationException()
    }

    override fun get(ctx: BindingGetSQLInputContext<Point>?) {
        throw UnsupportedOperationException()
    }

    override fun register(ctx: BindingRegisterContext<Point>?) {
        throw UnsupportedOperationException()
    }

}