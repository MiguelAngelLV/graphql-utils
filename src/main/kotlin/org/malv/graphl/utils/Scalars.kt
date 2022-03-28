package org.malv.graphl.utils

import graphql.language.StringValue
import graphql.schema.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*


public object Scalars {

    public fun getDate(): GraphQLScalarType {

        val coercing = object : Coercing<Date, String> {

            override fun serialize(input: Any): String {
                if (input is Date)
                    return dateFormatter.format(input)

                throw CoercingSerializeException("Get $input")
            }


            override fun parseValue(input: Any): Date {

                if (input is Date)
                    return input

                if (input is String)
                    return dateFormatter.parse(input)


                throw CoercingParseValueException("Get $input")

            }

            override fun parseLiteral(input: Any): Date {

                if (input !is StringValue)
                    throw CoercingParseLiteralException("Expected AST type 'StringValue'. Get $input ")


                return parseValue(input.value)
            }



        }
        return GraphQLScalarType
            .newScalar()
            .name("Date")
            .description("An RFC-3339 compliant Full Date Scalar")
            .coercing(coercing)
            .build()

    }

    public fun getDateTime(format: String): GraphQLScalarType {

        val coercing = object : Coercing<Date, String> {
            val formatter = SimpleDateFormat(format)

            override fun serialize(input: Any): String {
                if (input is Date)
                    return formatter.format(input)

                throw CoercingSerializeException("Get $input")
            }


            override fun parseValue(input: Any): Date {

                if (input is Date)
                    return input

                if (input is String)
                    return formatter.parse(input)


                throw CoercingParseValueException("Get $input")

            }

            override fun parseLiteral(input: Any): Date {

                if (input !is StringValue)
                    throw CoercingParseLiteralException("Expected AST type 'StringValue'. Get $input ")


                return parseValue(input.value)
            }

        }
        return GraphQLScalarType
            .newScalar()
            .name("DateTime")
            .description("An RFC-3339 compliant Full Date Scalar")
            .coercing(coercing)
            .build()

    }


    public fun getFile(): GraphQLScalarType {

        val coercing = object : Coercing<ByteArray, String> {

            override fun serialize(input: Any): String {
                if (input is ByteArray)
                    return String(Base64.getEncoder().encode(input), StandardCharsets.US_ASCII)

                throw CoercingSerializeException("Get $input")
            }


            override fun parseValue(input: Any): ByteArray {

                if (input is ByteArray)
                    return input

                if (input is String)
                    return Base64.getDecoder().decode(input)


                throw CoercingParseValueException("Get $input")

            }

            override fun parseLiteral(input: Any): ByteArray {

                if (input !is StringValue)
                    throw CoercingParseLiteralException("Expected AST type 'StringValue'. Get $input ")


                return parseValue(input.value)
            }

        }
        return GraphQLScalarType
            .newScalar()
            .name("File")
            .description("Base64 file")
            .coercing(coercing)
            .build()

    }


    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    private val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    private val dateMillisFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")


}

