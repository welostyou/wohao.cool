package utils

import io.ktor.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext

typealias PContext = PipelineContext<Unit, ApplicationCall>