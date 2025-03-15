import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { logInfo } from '@prep/shared';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  await app.listen(3000);
  logInfo('Server is running on http://localhost:3000');
}
bootstrap();
